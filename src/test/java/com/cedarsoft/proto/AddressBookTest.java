package com.cedarsoft.proto;

import com.example.tutorial.AddressBookProtos;
import com.example.tutorial.AddressBookProtos.Person;
import org.assertj.core.api.Assertions;
import org.junit.*;

import java.nio.charset.StandardCharsets;

/**
 * @author Johannes Schneider (<a href="mailto:js@cedarsoft.com">js@cedarsoft.com</a>)
 */
public class AddressBookTest {

  @Test
  public void basics() throws Exception {
    String name = "Markus Mustermann";
    String mail = "test@testaer.de";
    String phoneNumber = "07072 9229970";

    //calculate length
    int length = name.getBytes(StandardCharsets.UTF_8).length + 1
      + mail.getBytes(StandardCharsets.UTF_8).length + 1
      + phoneNumber.getBytes(StandardCharsets.UTF_8).length + 1
      + 4 //id
      + 1 //phone type
      ;

    Assertions.assertThat(length).isEqualTo(53);


    Person person = Person.newBuilder()
      .setId(1234)
      .setName(name)
      .setEmail(mail)
      .addPhone(
        Person.PhoneNumber.newBuilder()
          .setNumber(phoneNumber)
          .setType(Person.PhoneType.HOME)
      ).build();

    Assertions.assertThat(person.getPhoneList()).hasSize(1);
    Assertions.assertThat(person.toString()).isEqualTo("name: \"Markus Mustermann\"\n" +
                                                         "id: 1234\n" +
                                                         "email: \"test@testaer.de\"\n" +
                                                         "phone {\n" +
                                                         "  number: \"07072 9229970\"\n" +
                                                         "  type: HOME\n" +
                                                         "}\n");

    byte[] personArray = person.toByteArray();
    Assertions.assertThat(personArray).hasSize(58);

  }
}
