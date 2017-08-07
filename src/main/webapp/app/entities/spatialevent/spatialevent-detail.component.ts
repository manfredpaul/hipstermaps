import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { Spatialevent } from './spatialevent.model';
import { SpatialeventService } from './spatialevent.service';

@Component({
    selector: 'jhi-spatialevent-detail',
    templateUrl: './spatialevent-detail.component.html'
})
export class SpatialeventDetailComponent implements OnInit, OnDestroy {

    spatialevent: Spatialevent;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private spatialeventService: SpatialeventService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSpatialevents();
    }

    load(id) {
        this.spatialeventService.find(id).subscribe((spatialevent) => {
            this.spatialevent = spatialevent;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSpatialevents() {
        this.eventSubscriber = this.eventManager.subscribe(
            'spatialeventListModification',
            (response) => this.load(this.spatialevent.id)
        );
    }
}
