package com.simpleapp.map.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Place")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Place {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "node_id")
    private long nodeId;

    @Column(name = "name")
    private String name;

    public Place(long nodeId, String name) {
        this.nodeId = nodeId;
        this.name = name;
    }
}
