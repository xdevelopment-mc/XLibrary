// Adapted and improved from: https://github.com/nulli0n/nightcore-spigot
package net.xdevelopment.xlibrary.core.utility;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.*;
import java.util.*;

@UtilityClass
public class ReflectionUtility {

    public boolean classExists(@NotNull String path) {
        return findClass(path).isPresent();
    }

    @NotNull
    public Class<?> safeClass(@NotNull String path, @NotNull String name, @NotNull String altName) {
        return findClass(path, name, altName).orElseThrow(() -> new IllegalStateException("Could not load classes: '" + name + "' and '" + altName + "' in '" + path + "'"));
    }

    @NotNull
    public Class<?> safeClass(@NotNull String path, @NotNull String name) {
        return findClass(path, name).orElseThrow(() -> new IllegalStateException("Could not load class: '" + name + "' in '" + path + "'"));
    }

    @NotNull
    public Class<?> safeInnerClass(@NotNull String path, @NotNull String name) {
        return findInnerClass(path, name).orElseThrow(() -> new IllegalStateException("Could not load inner class: '" + name + "' in '" + path + "'"));
    }

    @NotNull
    public Class<?> safeClass(@NotNull String path) {
        return findClass(path).orElseThrow(() -> new IllegalStateException("Could not load class: '" + path + "'"));
    }

    @NotNull
    public Optional<Class<?>> findClass(@NotNull String path, @NotNull String name, @NotNull String altName) {
        return findClass(path, name).or(() -> findClass(path, altName));
    }

    @NotNull
    public Optional<Class<?>> findClass(@NotNull String path, @NotNull String name) {
        return findClass(path + "." + name);
    }

    @NotNull
    public Optional<Class<?>> findInnerClass(@NotNull String path, @NotNull String name) {
        return findClass(path + "$" + name);
    }

    @NotNull
    public Optional<Class<?>> findClass(@NotNull String path) {
        try {
            return Optional.of(Class.forName(path));
        } catch (ClassNotFoundException exception) {
            return Optional.empty();
        }
    }

    @NotNull
    public Optional<Constructor<?>> getConstructor(@NotNull Class<?> source, Class<?>... types) {
        try {
            Constructor<?> constructor = source.getDeclaredConstructor(types);
            constructor.setAccessible(true);
            return Optional.of(constructor);
        } catch (ReflectiveOperationException exception) {
            return Optional.empty();
        }
    }

    @NotNull
    public Optional<Object> invokeConstructor(@NotNull Constructor<?> constructor, Object... obj) {
        try {
            return Optional.of(constructor.newInstance(obj));
        } catch (ReflectiveOperationException exception) {
            return Optional.empty();
        }
    }

    @NotNull
    public <T> List<T> getStaticFields(@NotNull Class<?> source, @NotNull Class<T> type, boolean includeParent) {
        List<T> list = new ArrayList<>();

        for (Field field : getFields(source, includeParent)) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            if (!type.isAssignableFrom(field.getType())) continue;
            if (!field.trySetAccessible()) continue;

            try {
                list.add(type.cast(field.get(null)));
            } catch (IllegalArgumentException | IllegalAccessException ignored) {
            }
        }

        return list;
    }

    @NotNull
    public List<Field> getFields(@NotNull Class<?> source) {
        return getFields(source, true);
    }

    @NotNull
    public List<Field> getFields(@NotNull Class<?> source, boolean includeParent) {
        List<Field> result = new ArrayList<>();

        Class<?> lookupClass = source;
        while (lookupClass != null && lookupClass != Object.class) {
            if (!result.isEmpty()) {
                result.addAll(0, Arrays.asList(lookupClass.getDeclaredFields()));
            } else {
                Collections.addAll(result, lookupClass.getDeclaredFields());
            }
            if (!includeParent) {
                break;
            }
            lookupClass = lookupClass.getSuperclass();
        }

        return result;
    }

    @NotNull
    public Optional<Field> getField(@NotNull Class<?> source, @NotNull String name) {
        try {
            return Optional.of(source.getDeclaredField(name));
        } catch (NoSuchFieldException exception) {
            Class<?> superClass = source.getSuperclass();
            return superClass == null ? Optional.empty() : getField(superClass, name);
        }
    }

    @NotNull
    public Optional<Object> getFieldValue(@NotNull Object source, @NotNull String realName, @NotNull String obfName) {
        return getFieldValue(source, realName).or(() -> getFieldValue(source, obfName));
    }

    @NotNull
    public Optional<Object> getFieldValue(@NotNull Object source, @NotNull String name) {
        try {
            Class<?> clazz = source instanceof Class<?> ? (Class<?>) source : source.getClass();
            Optional<Field> optionalField = getField(clazz, name);
            if (optionalField.isEmpty()) return Optional.empty();

            Field field = optionalField.get();
            field.setAccessible(true);
            return Optional.ofNullable(field.get(source));
        } catch (IllegalAccessException exception) {
            return Optional.empty();
        }
    }

    public boolean setFieldValue(@NotNull Object source, @NotNull String name, @Nullable Object value) {
        try {
            boolean isStatic = source instanceof Class;
            Class<?> clazz = isStatic ? (Class<?>) source : source.getClass();

            Optional<Field> optionalField = getField(clazz, name);
            if (optionalField.isEmpty()) return false;

            Field field = optionalField.get();
            field.setAccessible(true);
            field.set(isStatic ? null : source, value);
            return true;
        } catch (IllegalAccessException exception) {
            return false;
        }
    }

    @NotNull
    public Method safeMethod(@NotNull Class<?> source, @NotNull String name, @NotNull String altName, @NotNull Class<?>... params) {
        return findMethod(source, name, altName, params).orElseThrow(() -> new IllegalStateException("Could not find methods: '" + name + "' and '" + altName + "' in '" + source.getName() + "'"));
    }

    @NotNull
    public Method safeMethod(@NotNull Class<?> source, @NotNull String name, @NotNull Class<?>... params) {
        return findMethod(source, name, params).orElseThrow(() -> new IllegalStateException("Could not find method: '" + name + "' in '" + source.getName() + "'"));
    }

    @NotNull
    public Optional<Method> findMethod(@NotNull Class<?> source, @NotNull String name, @NotNull String altName, @NotNull Class<?>... params) {
        return findMethod(source, name, params).or(() -> findMethod(source, altName, params));
    }

    @NotNull
    public Optional<Method> findMethod(@NotNull Class<?> source, @NotNull String name, @NotNull Class<?>... params) {
        try {
            return Optional.of(source.getDeclaredMethod(name, params));
        } catch (NoSuchMethodException exception) {
            Class<?> superClass = source.getSuperclass();
            return superClass == null ? Optional.empty() : findMethod(superClass, name);
        }
    }

    @NotNull
    public Optional<Object> safeInvoke(@NotNull Method method, @Nullable Object by, @Nullable Object... param) {
        try {
            method.setAccessible(true);
            return Optional.ofNullable(method.invoke(by, param));
        } catch (ReflectiveOperationException exception) {
            return Optional.empty();
        }
    }

    @Nullable
    public Object invokeMethod(@NotNull Method method, @Nullable Object by, @Nullable Object... param) {
        return safeInvoke(method, by, param).orElse(null);
    }
}
