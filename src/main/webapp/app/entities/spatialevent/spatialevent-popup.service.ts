import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Spatialevent } from './spatialevent.model';
import { SpatialeventService } from './spatialevent.service';

@Injectable()
export class SpatialeventPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private spatialeventService: SpatialeventService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.spatialeventService.find(id).subscribe((spatialevent) => {
                    spatialevent.date = this.datePipe
                        .transform(spatialevent.date, 'yyyy-MM-ddThh:mm');
                    this.ngbModalRef = this.spatialeventModalRef(component, spatialevent);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.spatialeventModalRef(component, new Spatialevent());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    spatialeventModalRef(component: Component, spatialevent: Spatialevent): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.spatialevent = spatialevent;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
