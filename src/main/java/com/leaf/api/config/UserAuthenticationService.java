package com.leaf.api.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements AuthenticationProvider {
   @Override
   public Authentication authenticate(Authentication auth) throws AuthenticationException
   {
      Authentication retVal = null;
      List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
      System.out.println("Authentication"+ auth);
      if (auth != null)
      {
         String name = auth.getName();
         String password = auth.getCredentials().toString();
         System.out.println("name: " + name);
         System.out.println("password: " + password);
         
         if (name.equals("admin") && password.equals("admin123"))
         {
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            
            retVal = new UsernamePasswordAuthenticationToken(
               name, "", grantedAuths
            );
            System.out.println("grant User");
         }
      }
      else
      {
         System.out.println("invalid login");
         retVal = new UsernamePasswordAuthenticationToken(
            null, null, grantedAuths
         );
         System.out.println("bad Login");
      }

      return retVal;
   }

   @Override
   public boolean supports(Class<?> tokenType)
   {
      return tokenType.equals(UsernamePasswordAuthenticationToken.class);
   }
}
