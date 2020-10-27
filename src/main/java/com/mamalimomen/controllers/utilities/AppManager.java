package com.mamalimomen.controllers.utilities;

import com.mamalimomen.base.controllers.utilities.PersistenceUnitManager;
import com.mamalimomen.base.controllers.utilities.PersistenceUnits;
import com.mamalimomen.base.domains.BaseEntity;
import com.mamalimomen.base.services.BaseService;
import com.mamalimomen.services.Impl.AccountServiceImpl;
import com.mamalimomen.services.Impl.CommentServiceImpl;
import com.mamalimomen.services.Impl.LikeServiceImpl;
import com.mamalimomen.services.Impl.PostServiceImpl;

import javax.persistence.EntityManager;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

public final class AppManager {
    private static final List<EntityManager> emList = new ArrayList<>();
    private static final Map<Services, BaseService<? extends Long, ? extends BaseEntity>> serviceMapper = new HashMap<>();

    private AppManager() {
    }

    public synchronized static void startApp() {
        try {
            System.setErr(new PrintStream("D:\\عزم راسخ\\جاوا مکتب\\کلاس\\40\\HW14_BackEnd\\src\\main\\resources\\log.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        EntityManager em = PersistenceUnitManager.getEntityManager(PersistenceUnits.UNIT_ONE);

        emList.add(em);

        serviceMapper.put(Services.ACCOUNT_SERVICE, new AccountServiceImpl(em));
        serviceMapper.put(Services.POST_SERVICE, new PostServiceImpl(em));
        serviceMapper.put(Services.COMMENT_SERVICE, new CommentServiceImpl(em));
        serviceMapper.put(Services.LIKE_SERVICE, new LikeServiceImpl(em));

        MenuFactory.getMenu(null).routerMenu();
    }

    public static <PK extends Long, E extends BaseEntity, S extends BaseService<PK, E>> S getService(Services service) {
        return (S) serviceMapper.get(service);
    }

    public static synchronized void endApp() {
        for (EntityManager em : emList) {
            em.close();
        }

        PersistenceUnitManager.closeAllPersistenceProviders();
    }
}
