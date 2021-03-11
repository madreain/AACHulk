package com.madreain.libhulk.click

import android.view.View
import com.madreain.libhulk.components.base.BaseActivity
import com.madreain.libhulk.components.base.BaseFragment
import com.madreain.libhulk.utils.ClickUtils
import java.lang.reflect.Method

object ClickInject {

    /**
     * OnClick注解方法，注测成点击事件
     */
    fun injectClickAnnotation(activity: BaseActivity) {
        injectClickAnnotation(activity, object : ViewComponent {
            override fun getView(id: Int): View? {
                return activity.findViewById(id)
            }

        })
    }


    /**
     * OnClick注解方法，注测成点击事件
     */
    fun injectClickAnnotation(fragment: BaseFragment) {
        injectClickAnnotation(fragment, object : ViewComponent {
            override fun getView(id: Int): View? {
                return fragment.view?.findViewById(id)
            }

        })
    }

    /**
     * OnClick注解方法，注测成点击事件
     */
    private fun injectClickAnnotation(obj: Any, viewComponent: ViewComponent) {
        val methods: Array<Method> = obj.javaClass.methods
        for (method in methods) {
            val onClick = method.getAnnotation(OnClick::class.java)
            onClick?.ids?.forEach {//通过反射api获取方法上面的注解
                if (it == -1) return
                viewComponent.getView(it)?.setOnClickListener { v ->
                    try {
                        if (ClickUtils.isClickAvailable() || !onClick.debounce) {//防抖模式会校验0.5s的防抖动
                            val typeParameters = method.parameterTypes
                            if (typeParameters.isEmpty()) {//此方法无参数
                                method.invoke(obj)
                            } else if (typeParameters.size == 1 && typeParameters[0] == View::class.java
                            ) {//此方法有参数
                                method.invoke(obj, v)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }


    interface ViewComponent {
        fun getView(id: Int): View?
    }
}