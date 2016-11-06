package it.softwarelabs.bank.application.methodargumentresolver;

import it.softwarelabs.bank.domain.user.Email;
import it.softwarelabs.bank.domain.user.User;
import it.softwarelabs.bank.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserResolver implements HandlerMethodArgumentResolver {

    private UserRepository userRepository;

    @Autowired
    public UserResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(
        MethodParameter methodParameter,
        ModelAndViewContainer modelAndViewContainer,
        NativeWebRequest nativeWebRequest,
        WebDataBinderFactory webDataBinderFactory
    ) throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(new Email(email));
    }
}
