package toyproject.board.domain.item;

import lombok.Getter;

import javax.persistence.Entity;

@Getter
@Entity
public class Bag extends Item{

    private String color;
}
