import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.EventListener;

public class Announcer<T extends EventListener> {
    private final T proxy;
    private final List<T> listeners = new ArrayList<>();

    // Construtor privado para forçar o uso do método de fábrica to().
    private Announcer(Class<T> listenerType) {
        this.proxy = listenerType.cast(Proxy.newProxyInstance(
                listenerType.getClassLoader(),
                new Class<?>[] { listenerType },
                (proxy, method, args) -> {
                    listeners.forEach(listener -> {
                        try {
                            method.invoke(listener, args);
                        } catch (Exception e) {
                            // O livro tipicamente sugere lidar ou lançar exceções aqui.
                            // Para simplicidade, apenas imprime ou ignora.
                            e.printStackTrace();
                        }
                    });
                    return null; // Event methods typically return void
                }
        ));
    }

    // Método de fábrica estático (similar a Announcer.to(Class))
    public static <T extends EventListener> Announcer<T> to(Class<T> listenerType) {
        return new Announcer<>(listenerType);
    }

    public void addListener(T listener) {
        listeners.add(listener);
    }

    // Retorna o proxy que se parece com o listener, mas anuncia para todos os listeners.
    public T announce() {
        return proxy;
    }

}
