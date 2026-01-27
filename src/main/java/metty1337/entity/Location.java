package metty1337.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode(of = {"name", "latitude", "longitude"})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "locations", schema = "public")
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false, length = 40)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "latitude", nullable = false, precision = 9, scale = 6)
  private BigDecimal latitude;

  @Column(name = "longitude", nullable = false, precision = 9, scale = 6)
  private BigDecimal longitude;

  public Location(String name, User user, BigDecimal latitude, BigDecimal longitude) {
    this.name = name;
    this.user = user;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
