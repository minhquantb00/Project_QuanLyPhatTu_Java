package QL_Chua.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "otpemail")
public class OtpEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String otp;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "otpemail")
    @JsonIgnoreProperties(value = "otpEmail")
    PhatTus phatTu;
}
