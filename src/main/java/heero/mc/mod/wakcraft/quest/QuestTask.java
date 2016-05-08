package heero.mc.mod.wakcraft.quest;

public class QuestTask {
    public String action;
    public String description;
    public String to;
    private String[] dialog;
    public QuestStack[] what;
    public QuestReward[] rewards;

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
