package org.chobit.trino;

/**
 * 执行操作时的阶段性回调
 *
 * @param <T> 泛型类型
 * @author zhangrui
 * @since 2025/3/27
 */
public interface StageCallback<T> {

    void onStage(T info);

}
