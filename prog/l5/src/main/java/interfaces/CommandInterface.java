package interfaces;

/**
 * Интерфейс для команды
 */
public interface CommandInterface {
    public String getDescription();
    public int exec(String[] args);
}
