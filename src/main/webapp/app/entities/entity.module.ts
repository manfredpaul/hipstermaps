import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HipstermapsSpatialeventModule } from './spatialevent/spatialevent.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        HipstermapsSpatialeventModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HipstermapsEntityModule {}
