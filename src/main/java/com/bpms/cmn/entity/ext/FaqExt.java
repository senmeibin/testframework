package com.bpms.cmn.entity.ext;

import com.bpms.cmn.entity.Faq;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cmn_faq")
public class FaqExt extends Faq {
    private static final long serialVersionUID = -20161121104221592L;
}