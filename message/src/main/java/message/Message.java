package message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class Message implements Serializable {
    public String message;
    public boolean isError;
    public String user;

    public Message(String message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }

    @Serial
    private static final long serialVersionUID = 1L;
}
