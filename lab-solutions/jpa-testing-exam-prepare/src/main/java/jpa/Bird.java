package jpa;

import javax.persistence.*;

@Entity
@Table(name = "birds")
public class Bird {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bird_id")
    private Long id;

    @Enumerated(EnumType.STRING)
//    @Column(name = "species")
    private BirdSpecies species;

    @ManyToOne
    @JoinColumn(name = "nest_id")
    private Nest nest;

    public Bird() {
    }

    public Bird(BirdSpecies species) {
        this.species = species;
    }

    public Bird(BirdSpecies species, Nest nest) {
        this.species = species;
        this.nest = nest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BirdSpecies getSpecies() {
        return species;
    }

    public void setSpecies(BirdSpecies species) {
        this.species = species;
    }

    public Nest getNest() {
        return nest;
    }
}
