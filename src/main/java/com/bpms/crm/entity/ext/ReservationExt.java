package com.bpms.crm.entity.ext;

import com.bpms.crm.entity.Reservation;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "crm_reservation")
public class ReservationExt extends Reservation {
    private static final long serialVersionUID = -20180407161811895L;
}