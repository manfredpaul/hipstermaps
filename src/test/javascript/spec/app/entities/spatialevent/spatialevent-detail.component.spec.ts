/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { HipstermapsTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SpatialeventDetailComponent } from '../../../../../../main/webapp/app/entities/spatialevent/spatialevent-detail.component';
import { SpatialeventService } from '../../../../../../main/webapp/app/entities/spatialevent/spatialevent.service';
import { Spatialevent } from '../../../../../../main/webapp/app/entities/spatialevent/spatialevent.model';

describe('Component Tests', () => {

    describe('Spatialevent Management Detail Component', () => {
        let comp: SpatialeventDetailComponent;
        let fixture: ComponentFixture<SpatialeventDetailComponent>;
        let service: SpatialeventService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HipstermapsTestModule],
                declarations: [SpatialeventDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SpatialeventService,
                    JhiEventManager
                ]
            }).overrideTemplate(SpatialeventDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SpatialeventDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpatialeventService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Spatialevent(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.spatialevent).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
