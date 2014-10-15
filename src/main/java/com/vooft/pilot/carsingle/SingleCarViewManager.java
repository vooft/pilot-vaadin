package com.vooft.pilot.carsingle;

import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationManager.NavigationEvent;
import com.vaadin.addon.touchkit.ui.NavigationManager.NavigationEvent.Direction;
import com.vaadin.addon.touchkit.ui.NavigationManager.NavigationListener;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.ui.Button.*;
import com.vaadin.ui.*;
import com.vooft.pilot.common.CarData;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 24.05.13
 * Time: 3:16
 * To change this template use File | Settings | File Templates.
 */
public class SingleCarViewManager implements NavigationListener, ClickListener {
    private NavigationManager nav;

    private List<CarData> carDataList;
    private int currentPosition;

    private NavigationView views[];
    private List<Component> backupComponents = new ArrayList<Component>();

    public SingleCarViewManager(List<CarData> carDataList, int currentPosition, final NavigationManager nav) {
        this.nav = nav;
        this.carDataList = carDataList;
        this.currentPosition = currentPosition;

        this.views = createViews(carDataList);

        //nav.addListener(this);
    }

    public void show() {
        Stack<Component> stack = nav.getViewStack();
        for(int i=0; i<stack.size(); i++)
            backupComponents.add(stack.get(i));

        if(backupComponents.contains(nav.getPreviousComponent())==false)
            backupComponents.add(nav.getPreviousComponent());

        if(backupComponents.contains(nav.getCurrentComponent())==false)
            backupComponents.add(nav.getCurrentComponent());

        nav.setPreviousComponent(views[getPreviousIndex(currentPosition)]);
        nav.setNextComponent(views[getNextIndex(currentPosition)]);

        nav.setCurrentComponent(views[currentPosition]);
    }

    private NavigationView[] createViews(List<CarData> carDataList) {
        NavigationView result[] = new NavigationView[carDataList.size()];

        for(int i=0; i<result.length; i++) {
            final CarData cd = carDataList.get(i);
            /*SingleCarView v = new SingleCarView(cd);

            NavigationButton backButton = new NavigationButton();
            backButton.addStyleName("back");
            backButton.setCaption("Назад");
            backButton.addListener(this);

            v.setLeftComponent(backButton);

            result[i] = v;*/
        }

        return result;
    }

    private int getPreviousIndex(int current) {
        int prevViewIndex = currentPosition - 1;
        if (prevViewIndex < 0) {
            prevViewIndex = views.length - 1;
        }

        return prevViewIndex;
    }

    private int getNextIndex(int current) {
        int nextViewIndex = currentPosition + 1;
        if (nextViewIndex >= views.length) {
            nextViewIndex = 0;
        }

        return nextViewIndex;
    }

    @Override
    public void navigate(NavigationEvent event) {
        //nav.getViewStack().pop();

        if (event.getDirection() == Direction.FORWARD) {
            currentPosition = getNextIndex(currentPosition);

            nav.setNextComponent(views[getNextIndex(currentPosition)]);
        } else {
            currentPosition = getPreviousIndex(currentPosition);

            nav.setPreviousComponent(views[getPreviousIndex(currentPosition)]);
        }
    }

    @Override
    public void buttonClick(ClickEvent event) {
        nav.removeListener(SingleCarViewManager.this);
        Stack<Component> stack = nav.getViewStack();
        while(stack.size()>0)
            stack.pop();

        Component prev = null;
        Component curr = null;
        Component next = null;

        if(backupComponents.size()>1) {
            prev = backupComponents.get(backupComponents.size()-2);
        }

        if(backupComponents.size()>0) {
            curr = backupComponents.get(backupComponents.size()-1);
        }

        for(Component c: backupComponents) {
            if(c==prev)
                continue;;

            if(c==curr)
                continue;

            if(c==next)
                continue;

            stack.push(c);
        }

        nav.setPreviousComponent(prev);
        nav.setNextComponent(next);
        nav.setCurrentComponent(curr);
    }
}
