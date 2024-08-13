package dev.onecheetah.zeta.util;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;

public final class Reflections {

  private Reflections() {
  }

  @SuppressWarnings("unchecked")
  public static <T> T noArgsNewInstance(Constructor<T> constructor) {
    try {
      return (T) MethodHandles.lookup()
          .unreflectConstructor(constructor)
          .asType(MethodType.methodType(Object.class)).invokeExact();
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> Constructor<T> getNoArgConstructor(Class<T> clazz) {
    try {
      return clazz.getDeclaredConstructor();
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }
}
