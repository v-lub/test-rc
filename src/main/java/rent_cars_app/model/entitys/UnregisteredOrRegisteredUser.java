package rent_cars_app.model.entitys;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnregisteredOrRegisteredUser {
    String email;
    String first_name;
    String second_name;
    String phone;
}
