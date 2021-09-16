package com.mahavastu.advisor.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "mv_client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientEntity
{

    @Id
    @Column(name = "cl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientId;

    @Column(name = "cl_name")
    private String clientName;

    @Column(name = "cl_mobile", unique = true)
    private String clientMobile;

    @Column(name = "cl_email", unique = true)
    private String clientEmail;

    @OneToOne
    @JoinColumn(name = "image_id")
    private ClientImageMasterEntity clientImageMasterEntity;

    @Column(name = "cl_poc")
    private String clientPOC;

    @OneToOne
    @JoinColumn(name = "occupation_id")
    private OccupationEntity occupation;

    @Column(name = "cl_password")
    private String password;
    
    private Timestamp createdDate;
    
    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressEntity addressEntity;
    
    @OneToOne
    @JoinColumn(name = "place_of_birth_address_id")
    private AddressEntity placeOfBirth;
    
    // yyyy-mm-dd,hh:mm:ss
    private String timeStampOfBirth;

    public ClientEntity(
            String clientName,
            String clientMobile,
            String clientEmail,
            ClientImageMasterEntity clientImageMasterEntity,
            String clientPOC,
            OccupationEntity occupation,
            String password)
    {
        this.clientName = clientName;
        this.clientMobile = clientMobile;
        this.clientEmail = clientEmail;
        this.clientImageMasterEntity = clientImageMasterEntity;
        this.clientPOC = clientPOC;
        this.occupation = occupation;
        this.password = password;
    }
}
