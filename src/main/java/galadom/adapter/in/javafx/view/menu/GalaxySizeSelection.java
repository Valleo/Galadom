package galadom.adapter.in.javafx.view.menu;

final class GalaxySizeSelection {
    private static final int[] SIZES = {200, 500, 1000};
    private int selectedIndex = 1;

    int selectedSize() {
        return SIZES[selectedIndex];
    }

    void selectPrevious() {
        selectedIndex = Math.max(0, selectedIndex - 1);
    }

    void selectNext() {
        selectedIndex = Math.min(SIZES.length - 1, selectedIndex + 1);
    }
}