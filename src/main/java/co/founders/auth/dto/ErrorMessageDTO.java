package co.founders.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageDTO {

    String message;
    String details;
    int status;
    String timestamp;
}
