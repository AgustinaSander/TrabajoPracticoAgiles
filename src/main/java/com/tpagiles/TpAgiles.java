package com.tpagiles;

import com.tpagiles.controllers.UserController;
import com.tpagiles.dao.IUserDAO;
import com.tpagiles.dao.UserDAOImpl;
import com.tpagiles.gestores.GestorUser;
import com.tpagiles.models.EnumTypeIdentification;
import com.tpagiles.models.User;
import com.tpagiles.models.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Import({ClassConfig.class})
public class TpAgiles {

  @Autowired
  GestorUser gestorUser;

  @Component
  class DataSetup implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception{
      gestorUser.altaUsuario(new UserDto("Agustina", "Sander", "asander00@hotmail.com", EnumTypeIdentification.DNI.name(), "42331387", "hola"));
    }
  }

  public static void main(String[] args) {
    SpringApplication.run(TpAgiles.class, args);
  }

}

