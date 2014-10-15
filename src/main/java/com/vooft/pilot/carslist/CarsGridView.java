package com.vooft.pilot.carslist;

import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.*;
import com.vooft.pilot.carsingle.SingleCarView;
import com.vooft.pilot.common.CarData;
import com.vooft.pilot.common.CarsSql;
import com.vooft.pilot.common.UriListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 07.05.13
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
public class CarsGridView extends NavigationView implements ValueChangeListener {
    private static final int GRID_WIDTH = 3;
    private static final int START_SIZE = 15;
    private static final int PAGE_SIZE = 9;

    private static final String NO_BRAND_FILTER = "Все авто";
    private static final String PRESSED_STYLE = "pressed";

    private static enum Filter {FILTER_NONE, FILTER_ONLY_FREE, FILTER_ONLY_BUSY };

    private List<CarData> mData = null;
    private List<CarData> currentData = null;
    private Filter currentFilter = Filter.FILTER_NONE;
    private NavigationManager nav;
    private int minSum;
    private int maxSum;
    private String brand = NO_BRAND_FILTER;
    private GridLayout carsGrid;
    private int currentAmount = 0;
    private List<String> brandsList;

    public CarsGridView(final NavigationManager nav, int minSum, int maxSum) {
        this.minSum = minSum;
        this.maxSum = maxSum;
        this.nav = nav;
    }

    @Override
    public void attach() {
        super.attach();

        initComponents();

        Page.getCurrent().addUriFragmentChangedListener(new UriListener("cars"));
    }

    public Component initComponents() {
        mData = CarsSql.getCars(minSum, maxSum);

        brandsList = new ArrayList<String>();
        for(CarData cd: mData) {
            if(brandsList.contains(cd.getBrand())==false)
                if(cd.getBrand().length()==0)
                    continue;

                brandsList.add(cd.getBrand());
        }

        Collections.sort(brandsList);
        brandsList.add(0, NO_BRAND_FILTER);

        setContent(createGrid());
        setToolbar(createToolbar());

        return this;
    }

    private Component createToolbar() {
        final HorizontalLayout hl = new HorizontalLayout();
        hl.setWidth("100%");

        ClickListener pressListener = new ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                Iterator<Component> it = hl.iterator();
                while(it.hasNext()) {
                    Component c = it.next();
                    if(c instanceof Button) {
                        Button btn = (Button) c;
                        if(btn==clickEvent.getButton()) {
                            btn.addStyleName(PRESSED_STYLE);
                        } else {
                            btn.removeStyleName(PRESSED_STYLE);
                        }
                    }
                }
            }
        };

        Button allCars = new Button("Все", pressListener);
        allCars.addStyleName("pilot-filter-all");
        allCars.setWidth(100.0f, Unit.PERCENTAGE);
        allCars.addStyleName(PRESSED_STYLE);
        allCars.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                currentFilter = Filter.FILTER_NONE;
                reset();
            }
        });
        hl.addComponent(allCars);
        hl.setComponentAlignment(allCars, Alignment.BOTTOM_LEFT);

        Button freeCars = new Button("Свободные", pressListener);
        freeCars.addStyleName("pilot-filter-free");
        freeCars.setWidth(100.0f, Unit.PERCENTAGE);
        freeCars.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                currentFilter = Filter.FILTER_ONLY_FREE;
                reset();
            }
        });
        hl.addComponent(freeCars);
        hl.setComponentAlignment(freeCars, Alignment.BOTTOM_CENTER);

        Button busyCars = new Button("Занятые", pressListener);
        busyCars.addStyleName("pilot-filter-busy");
        busyCars.setWidth(100.0f, Unit.PERCENTAGE);
        busyCars.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                currentFilter = Filter.FILTER_ONLY_BUSY;
                reset();
            }
        });
        hl.addComponent(busyCars);
        hl.setComponentAlignment(busyCars, Alignment.BOTTOM_RIGHT);

        return hl;
    }

    private List<CarData> filterData() {
        if(brand.equals(NO_BRAND_FILTER) && currentFilter==Filter.FILTER_NONE)
            return mData;

        List<CarData> result = new ArrayList<CarData>();
        if(brand.equals(NO_BRAND_FILTER)) {
            result.addAll(mData);
        } else {
            for(CarData cd: mData) {
                if(cd.getBrand().equals(brand))
                    result.add(cd);
            }
        }

        if(currentFilter!=Filter.FILTER_NONE) {
            boolean state = currentFilter==Filter.FILTER_ONLY_FREE;
            List<CarData> tmp = new ArrayList<CarData>();
            for(CarData cd: result) {
                if(cd.isFree()==state)
                    tmp.add(cd);
            }

            result = tmp;
        }

        return result;
    }

    private void appendNextPage() {
        int oldAmount = currentAmount;
        currentAmount += PAGE_SIZE;
        for(int i=oldAmount; i<currentData.size() && i<currentAmount; i++) {
            final CarData cd = currentData.get(i);

            carsGrid.addComponent(createCarItem(cd, i));
        }

        if(currentAmount<currentData.size()-1)
            ((AbstractComponentContainer) carsGrid.getParent()).addComponent(createPagingButton());
    }

    private Component createGrid() {
        CssLayout root = new CssLayout();
        root.addStyleName("pilot-grid-root");

        VerticalComponentGroup vl = new VerticalComponentGroup();
        vl.addStyleName("pilot-filter-layout");
        vl.setWidth("100%");

        ListSelect group = new ListSelect("Фильтр", brandsList);
        group.setNullSelectionAllowed(false);
        group.setInvalidAllowed(false);
        group.setImmediate(true);
        group.setValue(brand);
        //group.setWidth("90%");
        group.addValueChangeListener(CarsGridView.this);

        vl.addComponent(group);

        root.addComponent(vl);

        currentData = filterData();

        currentAmount = START_SIZE;

        carsGrid = new GridLayout(GRID_WIDTH, 1);
        carsGrid.setMargin(false);
        carsGrid.setSpacing(false);
        carsGrid.setWidth("100%");
        carsGrid.addStyleName("pilot-cars-grid");

        for(int i=0; i<currentData.size() && i<currentAmount; i++) {
            final CarData cd = currentData.get(i);
            Component item = createCarItem(cd, i);
            carsGrid.addComponent(item);
        }

        for(int i=0; i<GRID_WIDTH; i++)
            carsGrid.setColumnExpandRatio(i, 1);

        float height = 0;

        {
            Iterator<Component> it = carsGrid.iterator();
            while(it.hasNext()) {
                Component c = it.next();
                if(height<c.getHeight())
                    height = c.getHeight();
            }
        }

        {
            Iterator<Component> it = carsGrid.iterator();
            while(it.hasNext()) {
                Component c = it.next();
                c.setHeight(height, c.getHeightUnits());
            }
        }

        root.addComponent(carsGrid);

        if(currentAmount<currentData.size()-1)
            root.addComponent(createPagingButton());

        return root;
    }

    private Component createPagingButton() {
        final HorizontalLayout hl = new HorizontalLayout();
        hl.addStyleName("pilot-paging-button-layout");
        hl.setWidth("100%");

        final Button btn = new Button(String.format("Ещё? Ещё! (показано %d из %d)", currentAmount, mData.size()));
        btn.addStyleName("pilot-paging-button");
        //btn.setImmediate(true);
        btn.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                appendNextPage();
                Component c = hl.getParent();
                if(c instanceof AbstractComponentContainer) {
                    ((AbstractComponentContainer) c).removeComponent(hl);
                }
            }
        });

        hl.addComponent(btn);

        return hl;
    }

    private Component createCarItem(final CarData cd, int pos) {
        CarItem item = new CarItem(cd);

        final int finalI = pos;
        item.addLayoutClickListener(new LayoutClickListener() {
            @Override
            public void layoutClick(LayoutClickEvent event) {
                nav.navigateTo(new SingleCarView(currentData, finalI));
            }
        });

        item.setSizeFull();

        return item;
    }

    private void reset() {
        setContent(createGrid());
    }

    @Override
    public void valueChange(ValueChangeEvent event) {
        brand = (String) event.getProperty().getValue();

        reset();
    }
}
