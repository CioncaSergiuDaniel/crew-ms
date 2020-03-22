package com.abac.crew.bm.entity;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Builder(builderClassName = "Builder")
@Table(value = "team")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Crew {

    @PrimaryKey
    private long id;

    @Column(value = "external_id")
    @Indexed
    private String externalId;

    @Column(value = "captain_name")
    private String captainName;

    private String robots;
}
