package com.mahavastu.advisor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "mv_client_image_master")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientImageMasterEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;
    
    private Integer clientId;

    @Column(name = "cl_display_pic")
    @Lob
    private byte[] clientDisplayPic;

    public ClientImageMasterEntity(Integer clientId, byte[] clientDisplayPic)
    {
        super();
        this.clientId = clientId;
        this.clientDisplayPic = clientDisplayPic;
    }
    
    

}
