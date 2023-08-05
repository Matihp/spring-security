package com.security.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usuario")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    private String username;
    private String password;
    //Nos trae todos los roles y si se elimina se elimina todoo
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    //Se unen las tablas haciendo referencia al nombre de la columna y los ids de cada entidad
    @JoinTable(name="usuario_roles",joinColumns = @JoinColumn(name = "usuario_id",referencedColumnName = "id_usuario")
    ,inverseJoinColumns = @JoinColumn(name="rol_id",referencedColumnName = "id_rol"))
    private List<Roles> roles = new ArrayList<>();
 }
