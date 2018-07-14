<template>
  <v-container>
    <!--Header section-->
    <v-layout row wrap>
      <v-flex class=" mb-5" text-xs-center xs12>
        <span class="display-2">Worker registration system</span>
      </v-flex>
      <v-flex xs12 md12 text-xs-center>
        <v-btn color="primary" dark large @click="addNewWorker()" class="mb-2">Add new worker
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
            <td>{{ props.item.sector }}</td>
            <td>{{ props.item.agreedToTerms }}</td>
            <td>
              <v-btn icon class="mx-0" @click="editItem(props.item)">
                <v-icon color="teal">edit</v-icon>
              </v-btn>
              <v-btn icon class="mx-0" @click="deleteItem(props.item)">
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
                    <!--Sector section-->
                    <v-layout wrap>
                      <v-flex class="headline" xs12 md12>
                        <span slot="activator">Working sector</span>
                        <v-tooltip bottom>
                          <v-btn
                            slot="activator"
                            :key="icon"
                            :color="color"
                            dark
                            fab
                            small><b>?</b>
                          </v-btn>
                          <span>Choose working sector as precisely as possible. Picking industry is mandatory.</span>
                        </v-tooltip>
                      </v-flex>
                      <v-flex xs12 sm4 d-flex>
                        <v-select
                          :items="items2"
                          label="Industry"
                          v-model="editedItem.sector"
                          :rules="nonEmptyRule"
                        ></v-select>
                      </v-flex>
                      <v-flex xs12 sm4 d-flex>
                        <v-select
                          :items="items2"
                          label="Sector"
                          disabled
                        ></v-select>
                      </v-flex>
                      <v-flex xs12 sm4 d-flex>
                        <v-select
                          :items="items2"
                          label="Sub sector"
                          disabled
                        ></v-select>
                      </v-flex>
                      <v-flex xs12 sm4 d-flex>
                        <v-select
                          :items="items2"
                          label="Speciality"
                          disabled
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
                          <span>By accessing or using the Service you agree to be bound by these Terms. If you
                                disagree with any part of the terms then you may not access the Service.</span>
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
                            <v-card-title class="headline">Are you sure about deleting entry?
                            </v-card-title>
                            <v-card-actions>
                              <v-btn color="error" flat @click.native="confirmDeleteItem()">Yes</v-btn>
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
      items2: ['Foo', 'Bar', 'Fizz', 'Buzz'],
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
        {text: 'Sector', value: 'sector'},
        {text: 'Agreed to terms', value: 'agreedToTerms'},
        {text: 'Action', sortable: false}
      ],
      items: [],
      editedIndex: -1,
      editedItem: {
        firstName: '',
        lastName: '',
        sector: '',
        agreedToTerms: '',
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
      //this.items.push({agreedToTerms:true, firstName:'Deniss',lastName:'Potapenko',sector:'Fizz'});

      AXIOS.get('/workersall')
        .then(response => {
          this.items = response.data
          console.log(this.items);
        })
        .catch(e => {
          this.errors.push(e)
        });

    },
    methods: {
      initialize() {
        this.items = []
      },
      editItem(item) {
        console.log(item.agreedToTerms);
        this.editedIndex = item.id;
        this.editedItem = Object.assign({}, item)
        this.dialog = true
      },
      addNewWorker() {
        this.$refs.form.reset();
        this.dialog = true;
      },
      deleteItem(item) {
        this.confirmDialog = true;
        AXIOS.delete('/workers/' + item.id);
        this.deleteItemIndex = this.items.indexOf(item)
      },
      confirmDeleteItem() {
        this.items.splice(this.deleteItemIndex, 1);
        this.confirmDialog = false;
      },
      close() {
        this.dialog = false;
        setTimeout(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        }, 300)
      },
      save() {
        if (this.$refs.form.validate()) {
          if (this.editedIndex > -1) {
            Object.assign(this.items.find(item => item.id === this.editedIndex), this.editedItem);
            AXIOS.put('workers/' + this.editedIndex, {
              agreedToTerms: this.editedItem.agreedToTerms,
              firstName: this.editedItem.firstName,
              lastName: this.editedItem.lastName
            })
              .then(response => {
                console.log(response);
              })
              .catch(function (error) {
                console.log(error);
              });
          } else {
            AXIOS.post('workers/', {
              agreedToTerms: this.editedItem.agreedToTerms,
              firstName: this.editedItem.firstName,
              lastName: this.editedItem.lastName
            }).then(response => {
              console.log(response);
            })
              .catch(function (error) {
                console.log(error);
              });
            this.items.push(this.editedItem)
          }
          this.close();
        }
      }
    },
  }
</script>
