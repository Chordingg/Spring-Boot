package com.shop.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;

@Data
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class BaseTimeEntity {
}
