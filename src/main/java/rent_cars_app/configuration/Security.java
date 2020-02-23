package rent_cars_app.configuration;

import java.util.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import rent_cars_app.exceptions.BadRequest;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {
	
    @Bean
    public PasswordEncoder bcryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    	web.ignoring().antMatchers("/error");
    	web.ignoring().antMatchers("/registration");
    	web.ignoring().antMatchers(HttpMethod.GET, "/car");
    	web.ignoring().antMatchers("/search/**");
    	web.ignoring().antMatchers("/car/best");
        web.ignoring().antMatchers("/filters");
        web.ignoring().antMatchers("/comments");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.cors();
        http.httpBasic();
        http.csrf().disable();
        http.authorizeRequests().anyRequest().authenticated();
    }
    
    public static String[] credentials(String token) {
    	int pos = token.indexOf(" ");
		String auth = token.substring(pos + 1);
		String credential;
		String[] credentials = {""};
		try {
			byte[] decodeBytes = Base64.getDecoder().decode(auth);
			credential = new String(decodeBytes);
			credentials = credential.split(":");	
		} catch (Exception e) {
			throw new BadRequest("Wrong Autorization header value");
		}
		return credentials;
	}
    
    public static String passwordCode(String password) {
    	int pos = password.indexOf(" ");
		password = password.substring(pos + 1);
		try {
			byte[] decodeBytes = Base64.getDecoder().decode(password);
			password = new String(decodeBytes);
		} catch (Exception e) {
			throw new BadRequest("Wrong Autorization header value");
		}
		return password;
	}
}
