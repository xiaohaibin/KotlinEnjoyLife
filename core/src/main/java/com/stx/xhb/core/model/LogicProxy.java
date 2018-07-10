package com.stx.xhb.core.model;

import com.stx.xhb.core.model.annotation.Implement;
import com.stx.xhb.core.mvp.BasePresenter;
import com.stx.xhb.core.mvp.IBaseView;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class LogicProxy {

    private static final LogicProxy M_INSTANCE = new LogicProxy();

    public static LogicProxy getInstance() {
        return M_INSTANCE;
    }

    private LogicProxy() {
        mObjects = new HashMap<>();
    }

    private Map<Class, Object> mObjects;

    public void init(Class... clss) {
        for (Class cls : clss) {
            if (cls.isAnnotationPresent(Implement.class)) {
                for (Annotation ann : cls.getDeclaredAnnotations()) {
                    if (ann instanceof Implement) {
                        try {
                            mObjects.put(cls, ((Implement) ann).value().newInstance());
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    // 初始化presenter add map
    public <T> T bind(Class clzz, IBaseView var1) {
        if (!mObjects.containsKey(clzz)) {
            init(clzz);
        }
        BasePresenter presenter = ((BasePresenter) mObjects.get(clzz));
        if (var1 != presenter.getView()) {
            if (presenter.getView() != null) {
                presenter.detachView();
            }
            presenter.attachView(var1);
        }
        return (T) presenter;
    }

    // 解除绑定 移除map
    public void unbind(Class clzz, IBaseView var1) {
        if (mObjects.containsKey(clzz)) {
            BasePresenter presenter = ((BasePresenter) mObjects.get(clzz));
            if (var1 != presenter.getView()) {
                if (presenter.getView() != null) {
                    presenter.detachView();
                }
                mObjects.remove(clzz);
            }

        }
    }
}