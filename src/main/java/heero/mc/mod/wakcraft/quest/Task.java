package heero.mc.mod.wakcraft.quest;

public class Task {
    public String action;
    public String description;
    public String to;
    private String[] dialog;
    public Item[] what;
    public Reward[] rewards;

    public void setDialog(String dialog) {
        this.dialog = new String[]{dialog};
    }

    public void setDialog(String[] dialog) {
        this.dialog = dialog;
    }

    public String[] getDialog() {
        return dialog;
    }
}
