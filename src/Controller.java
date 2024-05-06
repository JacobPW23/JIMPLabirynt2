public class Controller {
    View view;
    Controller(View view)
    {
        this.view = view;
        view.addButtonListener(e -> {
            System.out.println("Button clicked");
        });
    }


}
