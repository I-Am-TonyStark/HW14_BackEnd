package com.mamalimomen.base.controllers.utilities;

import com.mamalimomen.base.dtos.BaseDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class ServerManager {
    private ServerSocket serverSocket;
    private final Stack<ClientThread> clients = new Stack<>();

    private final short port = 8000;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public ServerManager() {
        this.turnOnServer();
    }

    private void turnOnServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.err.println("Server is running on port " + port + "...");

            while (true) {
                Socket socket = serverSocket.accept();
                ClientThread client = new ClientThread(socket);
                clients.push(client);
                System.err.println("Current clients count : " + clients.size());
                executorService.execute(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void turnOffServer() {
        try {
            for (ClientThread client : clients) {
                client.closeClient();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientThread extends Thread {
        private final Socket socket;
        private final ObjectInputStream inputObject;
        private final ObjectOutputStream outputObject;

        private Optional<Object> buffer = Optional.empty();
        private final Lock accessLock = new ReentrantLock();
        private final Condition canTalk = accessLock.newCondition();
        private final Condition canListen = accessLock.newCondition();
        private boolean occupiedBuffer = false;

        ClientThread(Socket socket) throws IOException {
            this.socket = socket;
            outputObject = new ObjectOutputStream(socket.getOutputStream());
            inputObject = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public void run() {
            executorService.execute(this::listen);
            executorService.execute(this::talk);
        }

        private void listen() {
            while (true) {
                accessLock.lock();
                try {
                    while (occupiedBuffer) {
                        canListen.await();
                    }
                    buffer = Optional.of(MAMPParser.parse((MAMP<? extends BaseDTO<Long>>) inputObject.readObject()));
                    occupiedBuffer = true;
                    canTalk.signalAll();
                } catch (IOException | ClassNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    accessLock.unlock();
                }
            }
        }

        private void talk() {
            while (true) {
                accessLock.lock();
                try {
                    while (!occupiedBuffer) {
                        canTalk.await();
                    }
                    occupiedBuffer = false;
                    outputObject.writeObject(buffer.get());
                    buffer = Optional.empty();
                    canListen.signalAll();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    accessLock.unlock();
                }
            }
        }

        private void closeClient() throws IOException {
            inputObject.close();
            outputObject.close();
            socket.close();
        }
    }
}
