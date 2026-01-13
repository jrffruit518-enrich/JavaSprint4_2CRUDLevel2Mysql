package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ErrorResponse(int status, String message,
                            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                            LocalDateTime localDateTime) {
}
