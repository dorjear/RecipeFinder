package ador.recipe.domain;

import org.joda.time.LocalDate;
/**
 * Created by 43559616 on 3/1/2015.
 */
public class Ingredient2 {
    public String getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDate getUseBy() {
        return useBy;
    }

    public Ingredient2(){}

    public Ingredient2(String item, int amount, String unit) {
        this.item = item;
        this.amount = amount;
        this.unit = unit;
    }
    public Ingredient2(String item, int amount, String unit, LocalDate useBy) {
        this.item = item;
        this.amount = amount;
        this.unit = unit;
        this.useBy = useBy;
    }

    private String item;
    private int amount;
    private String unit;
    private LocalDate useBy;

}
