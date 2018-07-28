<template>
  <v-container>
    <!--Header section-->
    <v-layout row wrap>
      <v-flex class=" mb-5" text-xs-center xs12>
        <span class="display-2">Worker registration system</span>
      </v-flex>
      <v-flex xs12 md12 text-xs-center>
        <v-btn color="primary" dark large @click="addWorker()" class="mb-2">Add new worker
          <v-icon class="ml-2">add</v-icon>
        </v-btn>
      </v-flex>
    </v-layout>

    <!--Table section-->
    <v-layout>
      <v-flex xs12 md12>
        <v-text-field
          large
          append-icon="search"
          label="Search"
          v-model="search"
        ></v-text-field>
        <v-data-table
          :headers="headers"
          :items="items"
          :search="search"
          hide-actions
          class="elevation-1"
        >
          <template slot="items" slot-scope="props">
            <td>{{ props.item.firstName }}</td>
            <td>{{ props.item.lastName }}</td>
            <td>{{ props.item.sector.sectorName}}</td>
            <td>{{ props.item.agreedToTerms }}</td>
            <td>
              <v-btn icon class="mx-0" @click="editWorker(props.item)">
                <v-icon color="teal">edit</v-icon>
              </v-btn>
              <v-btn icon class="mx-0" @click="deleteWorker(props.item)">
                <v-icon color="pink">delete</v-icon>
              </v-btn>
            </td>
          </template>
        </v-data-table>
      </v-flex>
    </v-layout>

    <v-layout row justify-center>
      <v-form v-model="valid" ref="form">
        <v-dialog xs12 md12
                  v-model="dialog"
                  fullscreen
                  hide-overlay
                  transition="dialog-bottom-transition"
                  scrollable
                  @keydown.esc="dialog = false"
        >
          <v-card tile>
            <v-toolbar card dark color="primary">
              <v-spacer></v-spacer>
              <v-toolbar-items>
                <v-btn dark flat @click.native="save()">Save {{formTitle}}
                  <v-icon class="ml-2">save</v-icon>
                </v-btn>
              </v-toolbar-items>
              <v-spacer></v-spacer>
              <v-btn icon @click.native="dialog = false" dark>
                <v-icon>close</v-icon>
              </v-btn>
              <v-spacer></v-spacer>
            </v-toolbar>

            <v-card-text>
              <v-card>
                <v-card-text>
                  <v-container grid-list-md>
                    <!--Personal information section-->
                    <v-layout wrap>
                      <v-flex class="headline" xs12 md12>
                        <span slot="activator">Personal information</span>
                      </v-flex>
                      <v-flex xs12 md4>
                        <v-text-field label="First name" autofocus :rules="nonEmptyRule"
                                      v-model="editedItem.firstName"></v-text-field>
                      </v-flex>
                      <v-flex xs12 md4>
                        <v-text-field label="Last name" :rules="nonEmptyRule"
                                      v-model="editedItem.lastName"></v-text-field>
                      </v-flex>
                    </v-layout>
                    <v-spacer class="mb-3"></v-spacer>
                    <v-divider></v-divider>
                    <v-spacer></v-spacer>
                    <!--Working field section-->
                    <v-layout wrap>
                      <v-flex class="headline" xs12 md12>
                        <span slot="activator">Working field</span>
                        <v-tooltip bottom>
                          <v-btn
                            slot="activator"
                            :key="icon"
                            :color="color"
                            dark
                            fab
                            small><b>?</b>
                          </v-btn>
                          <span>Choose working field as precisely as possible. Picking industry is mandatory.</span>
                        </v-tooltip>
                      </v-flex>
                      <v-flex xs12 sm4 d-flex>
                        <v-select
                          :items="industryList.map(function(sectorName){return sectorName[0]})"
                          label="Industry"
                          v-model="selectedIndustry"
                          :rules="nonEmptyRule"
                          v-on:input="loadSectors(findFieldIdByFieldName(industryList,selectedIndustry))"
                        ></v-select>
                      </v-flex>
                      <v-flex xs12 sm4 d-flex v-if="isIndustrySelected">
                        <v-select
                          :items="sectorList.map(function(sectorName){return sectorName[0]})"
                          label="Sector"
                          v-model="selectedSector"
                          v-on:input="loadSubSectors(findFieldIdByFieldName(sectorList,selectedSector))"
                        ></v-select>
                      </v-flex>
                      <v-flex xs12 sm4 d-flex v-if="isSectorSelected">
                        <v-select
                          :items="subSectorList.map(function(sectorName){return sectorName[0]})"
                          v-model="selectedSubSector"
                          label="Subsector"
                          v-on:input="loadSpecialities(findFieldIdByFieldName(subSectorList,selectedSubSector))"
                        ></v-select>
                      </v-flex>
                      <v-flex xs12 sm4 d-flex v-if="isSubSectorSelected">
                        <v-select
                          :items="specialityList.map(function(sectorName){return sectorName[0]})"
                          label="Speciality"
                          v-model="selectedSpeciality"
                        ></v-select>
                      </v-flex>

                      <!--Agree to terms section-->
                    </v-layout>
                    <v-spacer class="mb-3"></v-spacer>
                    <v-divider></v-divider>
                    <v-spacer></v-spacer>
                    <v-layout wrap>
                      <v-flex class="headline" xs12 md12>
                        <span slot="activator">Terms of Service</span>
                        <v-tooltip bottom>
                          <v-btn
                            slot="activator"
                            :key="icon"
                            :color="color"
                            dark
                            fab
                            small><b>?</b>
                          </v-btn>
                          <span>By accessing or using the Service you agree to be bound by these Terms. By inserting personal information,
                            you must insert only valid information.</span>
                        </v-tooltip>
                      </v-flex>
                      <v-flex xs3 md12>
                        <v-switch
                          label="I accept the Terms of Service"
                          hide-details
                          v-model="editedItem.agreedToTerms"
                        ></v-switch>
                      </v-flex>
                    </v-layout>
                    <!--Delete entry modal section-->
                    <v-layout row wrap justify-center>
                      <v-flex xs12 md12>
                        <v-dialog @keydown.esc="confirmDialog = false" v-model="confirmDialog" max-width="450px">
                          <v-card>
                            <v-card-title class="headline">Are you sure about deleting worker?
                            </v-card-title>
                            <v-card-actions>
                              <v-btn color="error" flat @click.native="confirmDeleteWorker()">Yes</v-btn>
                              <v-btn color="primary" flat @click.native="confirmDialog = false">No</v-btn>
                            </v-card-actions>
                          </v-card>
                        </v-dialog>
                      </v-flex>
                    </v-layout>
                  </v-container>
                </v-card-text>
              </v-card>
            </v-card-text>
          </v-card>
        </v-dialog>
      </v-form>
    </v-layout>
  </v-container>
</template>

<script>
  import {AXIOS} from '../networking/http-common'

  export default {
    data: () => ({

      industryList: [],
      sectorList: [],
      subSectorList: [],
      specialityList: [],

      isIndustrySelected: false,
      isSectorSelected: false,
      isSubSectorSelected: false,
      isSpecialitySelected: false,

      selectedIndustry: '',
      selectedSector: '',
      selectedSubSector: '',
      selectedSpeciality: '',
      editedItemId: '',


      valid: true,
      menu: false,
      nonEmptyRule: [
        v => !!v || 'Field must be filled',
      ],
      dialog: false,
      confirmDialog: false,
      deleteItemIndex: 0,
      icon: 'help',
      search: '',
      color: 'blue',
      headers: [
        {text: 'First name', value: 'firstName'},
        {text: 'Last name', value: 'lastName'},
        {text: 'Working field', value: 'sector'},
        {text: 'Agreed to terms', value: 'agreedToTerms'},
        {text: 'Action', sortable: false}
      ],
      items: [],
      editedIndex: -1,

      editedItem: {
        firstName: '',
        lastName: '',
        sector: {},
        agreedToTerms: false,
      },
    }),
    computed: {
      formTitle() {
        return this.editedIndex === -1 ? 'new entry' : 'edited entry'
      }
    },
    watch: {
      dialog(val) {
        val || this.close()
      }
    },
    created() {
      AXIOS.get('/sector/1')
        .then(response => {
          this.industryList = response.data;
        })
        .catch(e => {
          this.errors.push(e)
        });
      this.fetchData()
    },


    methods: {
      initialize() {
        this.items = []
      },

      fetchData() {
        AXIOS.get('/workers')
          .then(response => {
            this.items = response.data
          })
          .catch(e => {
            this.errors.push(e)
          })
      },
      loadSectors(parent) {
        this.subSectorList = [];
        this.specialityList = [];
        this.isIndustrySelected = true;
        AXIOS.get('/sector/2/' + parent)
          .then(response => {
            this.sectorList = response.data;
          })
          .catch(e => {
            this.errors.push(e)
          });
      },
      loadSubSectors(parent) {
        this.specialityList = [];
        this.isSectorSelected = true;
        AXIOS.get('/sector/3/' + parent)
          .then(response => {
            this.subSectorList = response.data;
          })
          .catch(e => {
            this.errors.push(e)
          });
      },
      loadSpecialities(parent) {
        this.isSubSectorSelected = true;
        AXIOS.get('/sector/4/' + parent)
          .then(response => {
            this.specialityList = response.data;
          })
          .catch(e => {
            this.errors.push(e)
          });
      },
      editWorker(item) {
        this.resetData();
        this.editedIndex = item.id;
        this.editedItem.firstName = item.firstName;
        this.editedItem.lastName = item.lastName;
        this.editedItem.agreedToTerms = item.agreedToTerms;
        this.dialog = true
      },
      addWorker() {
        this.resetData();
        this.dialog = true;
      },
      deleteWorker(item) {
        this.confirmDialog = true;
        AXIOS.delete('/workers/' + item.id);
        this.deleteItemIndex = this.items.indexOf(item)
      },
      confirmDeleteWorker() {
        this.items.splice(this.deleteItemIndex, 1);
        this.confirmDialog = false;
      },
      resetData() {
        this.editedItem.firstName = '';
        this.editedItem.lastName = '';
        this.editedItem.sector = {};
        this.editedItem.agreedToTerms = false;

        this.isIndustrySelected = false;
        this.isSectorSelected = false;
        this.isSubSectorSelected = false;
        this.isSpecialitySelected = false;

        this.selectedIndustry = '';
        this.selectedSector = '';
        this.selectedSubSector = '';
        this.selectedSpeciality = '';
      },
      findFieldIdByFieldName(listOfSectors, sectorName) {
        for (let i = 0; i < listOfSectors.length; i++) {
          if (listOfSectors[i][0] === sectorName) {
            return listOfSectors[i][1];
          }
        }
      },
      close() {
        this.dialog = false;
        setTimeout(() => {
          this.editedIndex = -1
        }, 300)
      },
      save() {
        if (this.$refs.form.validate()) {
          let chosenField;
          let chosenFieldList;
          if (this.selectedSpeciality !== '') {
            chosenField = this.selectedSpeciality;
            chosenFieldList = this.specialityList;
          }
          else if (this.selectedSubSector !== '') {
            chosenField = this.selectedSubSector;
            chosenFieldList = this.subSectorList;
          }
          else if (this.selectedSector !== '') {
            chosenField = this.selectedSector;
            chosenFieldList = this.sectorList;
          } else {
            chosenField = this.selectedIndustry;
            chosenFieldList = this.industryList;
          }

          if (this.editedIndex > -1) {
            AXIOS.put('workers/' + this.editedIndex, {
                firstName: this.editedItem.firstName,
                lastName: this.editedItem.lastName,
                sector: {
                  id: this.findFieldIdByFieldName(chosenFieldList, chosenField),
                  sectorName: chosenField
                },
                agreedToTerms: this.editedItem.agreedToTerms
              }
            ).then(() => {
                this.fetchData();
              }
            ).catch(function (error) {
              console.log(error);
            });
          } else {
            AXIOS.post('workers/', {
              agreedToTerms: this.editedItem.agreedToTerms,
              firstName: this.editedItem.firstName,
              lastName: this.editedItem.lastName,
              sector: {
                id: this.findFieldIdByFieldName(chosenFieldList, chosenField),
                sectorName: chosenField
              }
            }).then(() => {
                this.fetchData();
              }
            ).catch((error) => {
              console.log(error);
            });
          }
          this.close();
        }
      }
    }
  }
</script>
