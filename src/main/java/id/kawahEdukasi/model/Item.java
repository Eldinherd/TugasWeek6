package id.kawahEdukasi.model;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "item")
public class Item extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "itemSequence", sequenceName = "item_sequence",allocationSize = 1, initialValue = 1 )
    @GeneratedValue(generator = "itemSequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    public long id;

    @Column(name = "name", nullable = false, length = 50)
    public String name;

    @Column(name = "count", nullable = false)
    public Double count;

    @Column(name = "price", nullable = false)
    public Double price;

    @Column(name = "type", nullable = false, length = 30)
    public String type;

    @Column(name = "description", columnDefinition = "text")
    public String description;

    @CreationTimestamp
    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;
}
