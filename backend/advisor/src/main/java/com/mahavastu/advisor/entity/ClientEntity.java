package com.mahavastu.advisor.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "mv_client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientEntity {

    @Id
    @Column(name = "cl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientId;

    @Column(name = "cl_name")
    private String clientName;

    @Column(name = "cl_mobile")
    private String clientMobile;

    @Column(name = "cl_email")
    private String clientEmail;

    @Column(name = "cl_display_pic")
    private String clientDisplayPic;

    @Column(name = "cl_poc")
    private String clientPOC;

    @OneToOne
    @JoinColumn(name = "occupation_id")
    private OccupationEntity occupation;

    @Column(name = "cl_password")
    private String password;

    public ClientEntity(String clientName, String clientMobile, String clientEmail, String clientDisplayPic, String clientPOC, OccupationEntity occupation, String password) {
        this.clientName = clientName;
        this.clientMobile = clientMobile;
        this.clientEmail = clientEmail;
        this.clientDisplayPic = clientDisplayPic;
        this.clientPOC = clientPOC;
        this.occupation = occupation;
        this.password = password;
    }
}
