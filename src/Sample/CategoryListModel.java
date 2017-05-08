package Sample;

import javax.swing.DefaultListModel;

class CategoryListModel extends DefaultListModel<String> {
    CategoryListModel() {
    }

    public void addSorted(String current) {
        int pos;
        for(pos = 0; pos < this.size() && ((String)this.get(pos)).compareTo(current) < 0; ++pos) {
            ;
        }

        this.add(pos, current);
    }
}

