package daniel.kiszel.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * request model to receive customer registration requests.
 * */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class PositionRequestDTO {
    @NotBlank(message = "Invalid Job name: Empty name")
    @NotNull(message = "Invalid Job name: Job name is NULL")
    @Size(max = 50, message = "Name must not exceed {max} characters")
    private String keyword;

    @NotBlank(message = "Invalid Location: Empty Location")
    @NotNull(message = "Invalid Location: Location is NULL")
    @Size(max = 50, message = "Location must not exceed {max} characters")
    private String location;
}
