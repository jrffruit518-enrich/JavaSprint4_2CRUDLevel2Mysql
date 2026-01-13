package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions;

public class ProviderExistsException extends RuntimeException {
    public ProviderExistsException(String message) {
        super(message);
    }
}
