package org.drools.workbench.screens.testscenario.client.reporting;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.drools.workbench.screens.testscenario.client.service.TestRuntimeReportingService;
import org.drools.workbench.screens.testscenario.model.Success;
import org.guvnor.common.services.shared.test.Failure;
import org.uberfire.client.annotations.DefaultPosition;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.workbench.model.CompassPosition;
import org.uberfire.workbench.model.Position;

import com.google.gwt.user.client.ui.Widget;

@ApplicationScoped
@WorkbenchScreen(identifier = "org.kie.guvnor.TestResults")
public class TestRunnerReportingScreen
        implements TestRunnerReportingView.Presenter {

    private final TestRunnerReportingView view;

    @Inject
    public TestRunnerReportingScreen(TestRunnerReportingView view,
                                     TestRuntimeReportingService testRuntimeReportingService) {
        this.view = view;
        view.setPresenter(this);
        view.bindDataGridToService(testRuntimeReportingService);
    }

    @DefaultPosition
    public Position getDefaultPosition() {
        return CompassPosition.SOUTH;
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return "Reporting";
    }

    @WorkbenchPartView
    public Widget asWidget() {
        return view.asWidget();
    }

    public void onSuccess(@Observes Success success){
        view.showSuccess();
        view.setExplanation("");
    }

    @Override
    public void onMessageSelected(Failure failure) {
        view.setExplanation(failure.getMessage());
    }

    @Override
    public void onAddingFailure(Failure failure) {
        view.showFailure();
    }
}
