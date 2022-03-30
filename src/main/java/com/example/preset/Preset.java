package com.example.preset;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity

public class Preset {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Ranking
    private String factor1;
    private String factor2;
    private String factor3;

    public String getFactor(int i){
        if(i == 1){
            return this.factor1;
        }else if(i == 2){
            return this.factor2;
        }else{
            return this.factor3;
        }
    }

    public Preset(String name ,User user, String factor1 , String factor2 , String factor3 ){
        this.name = name;
        this.factor1=factor1;
        this.factor2 = factor2;
        this.factor3 = factor3;
        this.user = user; 
    }
}


