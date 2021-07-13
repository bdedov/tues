import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule as NgRouterModule } from '@angular/router';
import { UpgradeModule as NgUpgradeModule } from '@angular/upgrade/static';
import { CoreModule, RouterModule, HOOK_NAVIGATOR_NODES, NavigatorNode} from '@c8y/ngx-components';
import {
  DashboardUpgradeModule,
  UpgradeModule,
  HybridAppModule,
  UPGRADE_ROUTES
} from '@c8y/ngx-components/upgrade';
import { AssetsNavigatorModule } from '@c8y/ngx-components/assets-navigator';
import { CockpitDashboardModule, ReportDashboardModule } from '@c8y/ngx-components/context-dashboard';
import { ReportsModule } from '@c8y/ngx-components/reports';
import { SensorPhoneModule } from '@c8y/ngx-components/sensor-phone';
import { BinaryFileDownloadModule } from '@c8y/ngx-components/binary-file-download';
import { HelloComponent } from './hello.component';


@NgModule({
  declarations: [HelloComponent],

  imports: [
    // Upgrade module must be the first
    UpgradeModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(),
    NgRouterModule.forRoot([{ path: 'hello', component: HelloComponent}, ...UPGRADE_ROUTES], { enableTracing: false, useHash: true }),
    CoreModule.forRoot(),
    AssetsNavigatorModule,
    ReportsModule,
    NgUpgradeModule,
    DashboardUpgradeModule,
    CockpitDashboardModule,
    SensorPhoneModule,
    ReportDashboardModule,
    BinaryFileDownloadModule
  ],

  providers: [
      {
        provide: HOOK_NAVIGATOR_NODES, // 1
        useValue: [{                   // 2
          label: 'Hello',              // 3
          path: 'hello',
          icon: 'rocket',
          priority: 1000
        }] as NavigatorNode[],         // 4
        multi: true                    // 5
      }
    ]
})
export class AppModule extends HybridAppModule {
  constructor(protected upgrade: NgUpgradeModule) {
    super();
  }
}
