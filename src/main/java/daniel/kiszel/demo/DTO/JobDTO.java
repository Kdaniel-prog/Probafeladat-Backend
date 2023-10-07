package daniel.kiszel.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JobDTO {
    private String name;
    private String location;
    private String url;
}
