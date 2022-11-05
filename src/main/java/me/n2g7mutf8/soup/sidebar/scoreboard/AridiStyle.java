package me.n2g7mutf8.soup.sidebar.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AridiStyle {

    MODERN(false, 1), KOHI(true, 15);

    private boolean descending;
    private int firstNumber;
}