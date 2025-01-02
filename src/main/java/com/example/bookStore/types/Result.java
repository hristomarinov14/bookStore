package com.example.bookStore.types;

import jakarta.validation.constraints.NotNull;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class Result<T, E extends Exception> {
    private final T value;
    private final E exception;

    private Result(T value, E exception) {
        this.value = value;
        this.exception = exception;
    }

    public static <T, E extends Exception> Result<T, E> createWithValue(@NotNull T value) {
       return new Result<>(value, null);
    }

    public static <T, E extends Exception> Result<T, E> createWithException(@NotNull E exception) {
        return new Result<>(null, exception);
    }

    public static <T, E extends Exception> Result<T, E> createFromOptional(@NotNull Optional<T> op) {
        if(op.isPresent()) {
            return createWithValue(op.get());
        }
        else {
            try {
                op.get();
            }catch (Exception ex) {
                return (Result<T, E>) createWithException(ex);
            }
        }

        throw new IllegalStateException("This state should not be possible!");
    }

    public void map(Consumer<T> ifValue, Consumer<E> ifException) {
        if(value != null) {
            ifValue.accept(value);
        }
        else if(exception != null) {
            ifException.accept(exception);
        }
        else {
            throw new IllegalStateException("This state should not be possible!");
        }
    }

    public <S> S mapResult(Function<T, S> ifValue, Function<E, S> ifException) {
        if(value != null) {
            return ifValue.apply(value);
        }
        else if(exception != null) {
            return ifException.apply(exception);
        }
        else {
            throw new IllegalStateException("This state should not be possible!");
        }
    }
}
