package com.quickbite.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 55)
    private String name;

    @Column(nullable = false, length = 55)
    private String email;

    @Column(nullable = false, length = 10)
    private Long phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;
}
